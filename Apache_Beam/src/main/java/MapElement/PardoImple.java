package MapElement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

//here we are filtering the only los angeles data from file

class CustFilter extends DoFn<String,String>
{
	@ProcessElement
	private void processElement(ProcessContext c) {
		// TODO Auto-generated method stub
		String line=c.element();

         String arr[]=line.split(",");
		
		if(arr[3].equals("Los Angeles"))
		{
			c.output(line);
		}
	}
}

public class PardoImple {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCust=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollection<String> pCustOutput=pCust.apply(ParDo.of(new CustFilter()));
		
		pCustOutput.apply(TextIO.write().to("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\UpperCaseOutput.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();
	}

}
