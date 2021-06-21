package functions;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class CountImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCustList=pipeline.apply(TextIO.read().from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\input.csv"));
		
		PCollection<Long> pLong=pCustList.apply(Count.globally());
		
		pLong.apply(ParDo.of(new DoFn<Long, Void>(){
			public void processElement(ProcessContext c)
			{
				System.out.println(c.element());
			}
		}));
		
		

	}

}
