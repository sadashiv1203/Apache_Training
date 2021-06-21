package Repositories;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
//import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
import Repositories.MyOptions;

public class OptionsImpl {

	public static void main(String[] args) 
	{
		MyOptions options=PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOptions.class);
		
		Pipeline pipeline=Pipeline.create(options);
        System.out.print("Done");
        PCollection<String> output=pipeline.apply(TextIO.read().from(options.getInputFile()));
        output.apply(TextIO.write().to(options.getOutputFile()).withNumShards(1).withSuffix(options.getExtn()));
        pipeline.run();

	}

}
