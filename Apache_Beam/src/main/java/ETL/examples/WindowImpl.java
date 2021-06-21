package ETL.examples;

import java.time.Duration;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.apache.kafka.common.serialization.LongDeserializer;

public class WindowImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		// About Kafka
		// is a distributed streaming platform
		
		//{"devId":"A","devName":"sadashiv"}
		
		
		//bin\windows\zookeeper-server-start config\zookeeper.properties
		//bin\windows\kafka-server-start config\server.properties
		//bin\windows\kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic beamtopic
		//bin\windows\kafka-console-producer --broker-list localhost:9092 --topic beamtopic
		//bin\windows\kafka-console-consumer --bootstrap-server localhost:9092 --topic beamtopic --from-beginning
		
		
		
		//kafka.apache.org
		
		Pipeline pipeline= Pipeline.create();
		
		pipeline.apply(KafkaIO.<Long,IOTEvent>read().withBootstrapServers("localhost:9092").withTopic("beamtopic")
				.withKeyDeserializer(LongDeserializer.class)
				.withValueDeserializer(IOTDeserializer.class)
				.withoutMetadata()
				)
		     .apply(Values.<IOTEvent>create())
		     .apply(Window.<IOTEvent>into(FixedWindows.of(org.joda.time.Duration.standardSeconds(10))))
		     .apply(ParDo.of(new DoFn<IOTEvent, String>()
		     {
		    	 @ProcessElement
    	         public void processElement(ProcessContext c) {
					  if(c.element().getDevName()=="sadashiv")
					  {
						  c.output(c.element().getDevName());
					  }
				   }
		     }))
		     .apply(Count.perElement()) // we can not use group key on unbounded data
		     .apply(ParDo.of(new DoFn<KV<String,Long>, Void>()
		    		  {
		    	         @ProcessElement
		    	         public void processElement(ProcessContext c) {
							// TODO Auto-generated method stub
		    	        	System.out.println("=======================================");
                            System.out.println(c.element());
						}
		    		  }));
		
		pipeline.run();
		System.out.println("Running................");

	

	}

}
