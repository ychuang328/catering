package com.weiwork.common.utils.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public final class IdHelperCenter implements ApplicationListener<ContextRefreshedEvent>,PrimarykeyGenerator{
	public static final String PREFIX_DB = "GET_PRIMERYKEY_OF_TABLE_DB_";
	public static final String PREFIX_TB = "_DB_";
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private static boolean firstStart=true;
	public static IdHelperCenter INSTANCE;
	private Long defaultCacheNum=10l;
	private RedisTemplate<String, Long> redis;
	
	private Map<String,IdsApply> idsApplyMap=new HashMap<String,IdsApply>();//存放已经申请到的id
	private Map<String,Long> idMinMap=new HashMap<String,Long>();//存放目前最小值
	private Map<String,String> sockMap=new HashMap<String,String>();  //存放表的锁对象
	private Map<String,IdHelper> idHelperMap=new HashMap<String,IdHelper>();  
	private List<IdHelper> idHelperList=new ArrayList<IdHelper>();
	public List<IdHelper> getIdHelperList() {
		return idHelperList;
	}
	public void setIdHelperList(List<IdHelper> idHelpperList) {
		this.idHelperList = idHelpperList;
	}
	public Map<String, IdHelper> getIdHelperMap() {
		return idHelperMap;
	}
	public void setRedis(RedisTemplate<String, Long> redisTemplate) {
		this.redis = redisTemplate;
	}
	
	
	private String keykey(String dbName,String tbName){
		return PREFIX_DB.concat(dbName).concat(PREFIX_TB).concat(tbName);
	}
	
	private IdHelper getIdHelper(String dbName,String tbName){
		String key=this.keykey(dbName, tbName);
		if(this.idHelperMap.containsKey(key)){
			return this.idHelperMap.get(key);
		}
		return null;
	}
	
	private Long initId(String dbName,String tbName){
		IdHelper helper=this.getIdHelper(dbName, tbName);
		Long maxId=helper.initId();
		return maxId;
	}
	
	//获取id
	private Long genId(String dbName,String tbName){
		final String key=keykey(dbName,tbName);
		if(!this.sockMap.containsKey(key)){
			synchronized (this.sockMap) {
				if(!this.sockMap.containsKey(key)){
					this.sockMap.put(key, key);
				}
			}
		}
		//每张表 一把锁
		String sockObj=this.sockMap.get(key);
		synchronized (sockObj) {
			if(!this.idMinMap.containsKey(key)){
				this.idMinMap.put(key, 0l);
			}
			Long valueDbMin=this.idMinMap.get(key);
			return this.applyFromLocalCache(key, valueDbMin);
		}
	}
	
	//从本地缓存策略里边取可用id
	private Long applyFromLocalCache(final String key,Long valueMin){
		if(this.idsApplyMap.containsKey(key)){
			IdsApply idsApply=idsApplyMap.get(key);
			while(idsApply.hasNext()){
				Long valueCacheLocal=idsApply.getNext();
				if(valueCacheLocal>valueMin){
					this.logger.debug("key:{},从本地缓存申请id:{}",key,valueCacheLocal);
					this.idMinMap.put(key, valueCacheLocal);
					return valueCacheLocal;
				}
			}
		}
		this.idsApplyMap.put(key, this.applyFromRedis(key, valueMin));
		return this.applyFromLocalCache(key, valueMin);
	}
	
	//从缓存申请一批id
	private IdsApply applyFromRedis(final String key,Long valueMin){
		final Long cacheNum;
		if(this.idHelperMap.containsKey(key)){
			cacheNum=this.idHelperMap.get(key).getApplyNum();
		}else{
			cacheNum=this.defaultCacheNum;
		}
		
		Long valueCacheRedis = redis.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				@SuppressWarnings("synthetic-access")
				byte[] keyByte = redis.getStringSerializer().serialize(key);
				return connection.incrBy(keyByte,cacheNum);
			}
		});
		if(valueCacheRedis<valueMin){
			redis.getConnectionFactory().getConnection().set(key.getBytes(), valueMin.toString().getBytes());
			return this.applyFromRedis(key, valueMin);
		}
		Long redisRealValue=valueCacheRedis-cacheNum+1;
		Long realCacheNum=cacheNum;
		if(redisRealValue<=valueMin){
			redisRealValue=valueMin+1;
			realCacheNum=valueCacheRedis-valueMin;
		} 
		this.logger.debug("key:{},从redis缓存申请id:{},num:{}",key,redisRealValue,realCacheNum);
		return new IdsApply(redisRealValue,realCacheNum);
	}
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(!firstStart){
			return;
		}
		for(IdHelper idh:this.idHelperList){
			String key=keykey(idh.getDbName(),idh.getTbName());
			this.idHelperMap.put(key, idh);
			this.idMinMap.put(key, initId(idh.getDbName(),idh.getTbName()));
		}
		firstStart=false;
		INSTANCE=this;
	}
	
	public void initForErr(String dbName,String tbName){
		Long minId=this.initId(dbName, tbName);
		String key=this.keykey(dbName, tbName);
		this.idMinMap.put(key, minId);
		this.logger.debug("重新设置db:{},tb:{},minValue:{}",dbName,tbName,minId);
	}
	
	@Override
	public Long generateId(String dbName, String tableName) {
		return this.genId(dbName,tableName);
	}
	
	

}
