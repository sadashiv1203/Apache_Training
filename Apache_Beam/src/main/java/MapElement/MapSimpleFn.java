package MapElement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

class User extends SimpleFunction<String, String>
{
	@Override
	public String apply(String input) {
		// TODO Auto-generated method stub
		
		String arr[]=input.split(",");
		
		String SId=arr[0];
		String sName=arr[1];
		String sGender=arr[2];
		
		String output="";
		
		if(sGender.equals("1"))
		{
			output=SId+","+sName+","+"M";
		}
		else
		{
			output=SId+","+sName+","+"F";
		}
		
		return output;
	}
}

public class MapSimpleFn {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
	Pipeline pipeline=Pipeline.create();
			
	PCollection<String> pCust=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
	
	PCollection<String> pCusOutputt=pCust.apply(MapElements.via(new User()));
		
	pCusOutputt.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\UpperCaseOutput.csv").withNumShards(1).withSuffix(".csv"));
	
	pipeline.run();
	}

}
