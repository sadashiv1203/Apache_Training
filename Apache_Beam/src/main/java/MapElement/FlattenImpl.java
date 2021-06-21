package MapElement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

public class FlattenImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCust1=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollection<String> pCust2=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollection<String> pCust3=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollectionList<String> list=PCollectionList.of(pCust1).and(pCust2).and(pCust3);
		
		PCollection<String> merged=list.apply(Flatten.pCollections());
		
		merged.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\UpperCaseOutput.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();

	}

}
