package parquet.examples;

import org.apache.avro.generic.GenericRecord;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

class printElement extends SimpleFunction<GenericRecord,Void>
{
	@Override
	public Void apply(GenericRecord input) {
		// TODO Auto-generated method stub
		//return super.apply(input);
		
		System.out.println(input.get("sessionId"));
		
		return null;
	}
}

public class ReadFromParquetExample {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		Pipeline pipeline= Pipeline.create();
		
		Schema schema; // =BeamCus  need to define schema class ;
		
		//need to add library
		
		PCollection<GenericRecord> poutput=pipeline.apply(ParquetIO.read(schema).from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\UpperCaseOutput.parquet"));
		
		poutput.apply(MapElements.via(new printElement()));
		
		pipeline.run();
		
		
		

	}

}
