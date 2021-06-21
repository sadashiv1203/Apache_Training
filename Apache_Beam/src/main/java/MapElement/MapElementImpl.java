package MapElement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;

public class MapElementImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCust=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		PCollection<String> pCustOut= pCust.apply(MapElements.into(TypeDescriptors.strings()).via((String obj)->obj.toUpperCase()));
		
		pCustOut.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\UpperCaseOutput.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();
	}

}
