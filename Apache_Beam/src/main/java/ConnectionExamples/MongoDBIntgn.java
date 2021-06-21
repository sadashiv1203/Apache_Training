package ConnectionExamples;



import java.util.HashMap;
import java.util.Map;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.bson.Document;

public class MongoDBIntgn {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//install Mongo DB
		
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pInput=pipeline.apply(TextIO.read().from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\input.csv"));
		
		PCollection<Document> pDocument=pInput.apply(ParDo.of(new DoFn<String, Document>()
				{
			        @ProcessElement
			        public void processElement(ProcessContext c)
			        {
			        	String arr[]=c.element().split(",");
			        	Map<String,Object> mapDocument=new HashMap<String,Object>();
			        	
			        	mapDocument.put("userId", arr[0]);
			        	mapDocument.put("orderId", arr[1]);
			        	mapDocument.put("Name", arr[2]);
			        	mapDocument.put("ProductId", arr[3]);
			        	
			        	Document d1=new Document(mapDocument);
			        	c.output(d1);
			        	
			        }
			        
				}));
		
		pDocument.apply(MongoDbIO.write().withUri("mongodb://localhost:27017").withDatabase("training").withCollection("user"));
		
	
		
		pipeline.run();

	}

}
