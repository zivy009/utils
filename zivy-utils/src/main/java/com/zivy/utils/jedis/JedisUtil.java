package com.zivy.utils.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zivy.utils.exception.MyRuntimeException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
	/**
	 * 保存map对象进入 redis
	 * 
	 * zivy
	 *
	 * 
	 * @param jedisPool
	 * @param key
	 * @param map
	 *            2016年12月21日
	 */
	public void hset(JedisPool jedisPool, String key, Map<String, String> map) {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, map);

		} catch (Exception e) {
			throw new RuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	/**
	 * 获得自增值。  
	 *
	 * 
	 * @param jedisPool
	 * @param key
	 * @return 2016年12月21日
	 */
	public Long incr(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		Long incrLong=null;
		
		try {
			jedis = jedisPool.getResource();
			incrLong=jedis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
			return incrLong;
		}
		 
	}

	/**
	 * 获得map对象
	 * 
	 * zivy
	 *
	 * 
	 * @param jedisPool
	 * @param key
	 * @return 2016年12月21日
	 */
	public Map<String, String> hget(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		Map<String, String> map = null;
		try {
			jedis = jedisPool.getResource();
			map = jedis.hgetAll(key);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
			return map;
		}

	}

	public String getStringValueByKey(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			throw new MyRuntimeException("获取 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public String get(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();

			return jedis.get(key);
		} catch (Exception e) {
			throw new MyRuntimeException("获取 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public Boolean exists(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			throw new MyRuntimeException("获取 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public static void appendStringValueByKey(JedisPool jedisPool, String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();

			jedis.append(key, value);
		} catch (Exception e) {
			throw new MyRuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public static void setStringValueByKey(JedisPool jedisPool, String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();

			jedis.set(key, value);
		} catch (Exception e) {
			throw new MyRuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public void set(JedisPool jedisPool, String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);

		} catch (Exception e) {
			e.printStackTrace();
			throw new MyRuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	/**
	 * 
	 * 带过期时间
	 * 
	 * zivy
	 *
	 * 
	 * @param jedisPool
	 * @param key
	 * @param value
	 * @param seconds
	 *            2016年12月20日
	 */
	public void setex(JedisPool jedisPool, String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// jedis.set(key, value);
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyRuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public List<String> lrange(JedisPool jedisPool, String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			throw new MyRuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public Long llen(JedisPool jedisPool, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.llen(key);
		} catch (Exception e) {
			throw new MyRuntimeException("llen 失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public List<String> gets(JedisPool jedisPool, String... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			ArrayList<String> list = new ArrayList<String>();

			for (String key : keys) {
				String val = jedis.get(key);
				list.add(val);
			}
			return list;
		} catch (Exception e) {
			throw new MyRuntimeException("设置 Jedis 数据失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public String hget(JedisPool jedisPool, String key, String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hget(key, field);
		} catch (Exception e) {
			throw new MyRuntimeException("hget 失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public Long hset(JedisPool jedisPool, String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hset(key, field, value);
		} catch (Exception e) {
			throw new MyRuntimeException("hset 失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}

	public Long hincrBy(JedisPool jedisPool, String key, String field, long value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			throw new MyRuntimeException("hincrBy 失败.");
		} finally {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
	}
}
