package MapElement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.PCollection;

class MyFilter implements SerializableFunction<String, Boolean>
{
	@Override
	public Boolean apply(String input) {
		// TODO Auto-generated method stub
	
		return input.contains("Los Angeles");
	}
}

public class FilterImpl 
{
   public static void main(String[] args) 
   {
	    Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCust=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollection<String> pCusOutputt=pCust.apply(Filter.by(new MyFilter()));
		
		pCusOutputt.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\UpperCaseOutput.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();
   }
}
