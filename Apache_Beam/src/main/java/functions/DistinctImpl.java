package functions;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Distinct;
import org.apache.beam.sdk.values.PCollection;

public class DistinctImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCustList=pipeline.apply(TextIO.read().from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\input.csv"));
		
		PCollection<String> uniqueCast=pCustList.apply(Distinct.<String>create());
		
		uniqueCast.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\output.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();
	}

}
