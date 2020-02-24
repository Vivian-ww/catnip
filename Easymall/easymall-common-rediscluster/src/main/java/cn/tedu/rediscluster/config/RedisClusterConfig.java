package cn.tedu.rediscluster.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
@ConfigurationProperties(prefix="spring.redis.cluster")
public class RedisClusterConfig {
	
	private List<String> nodes;
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	
	//初始化方法
	@Bean
	public JedisCluster initJedisCluster() {
		Set<HostAndPort> set = new HashSet<HostAndPort>();
		for(String node : nodes){
			String host = node.split(":")[0];
			Integer port = Integer.parseInt(node.split(":")[1]);
			set.add(new HostAndPort(host, port));
		}
		
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxTotal(maxTotal);
		
		return new JedisCluster(set, config);
	}
	
	public List<String> getNodes() {
		return nodes;
	}
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	public Integer getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	@Override
	public String toString() {
		return "RedisClusterConfig [nodes=" + nodes + ", maxTotal=" + maxTotal + ", maxIdle=" + maxIdle + ", minIdle="
				+ minIdle + "]";
	}
}
