package apache.beam.demo;

//import org.apache.beam.model.pipeline.v1.RunnerApi.PCollection;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;

import Repositories.MyOptions;

public class PCollectionDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Pipeline pipeline=Pipeline.create();
        System.out.print("Done");
        PCollection<String> output=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
        output.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\output.csv").withNumShards(1).withSuffix(".csv"));
        pipeline.run();

	}

}
