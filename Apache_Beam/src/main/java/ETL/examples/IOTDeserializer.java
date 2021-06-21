package ETL.examples;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IOTDeserializer implements Deserializer<IOTEvent>
{

	@Override
	public IOTEvent deserialize(String topic, byte[] data) {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper= new ObjectMapper();
		IOTEvent iotEven=null;
		try
		{
			iotEven = objectMapper.readValue(data, IOTEvent.class);
			
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
		return iotEven;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		Deserializer.super.close();
	}
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		Deserializer.super.configure(configs, isKey);
	}

}
