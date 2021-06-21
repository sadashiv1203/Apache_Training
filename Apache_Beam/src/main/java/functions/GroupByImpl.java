package functions;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

class StringToKV extends DoFn<String,KV<String,Integer>>
{
	@ProcessElement
	private void processElement(ProcessContext c) {
		// TODO Auto-generated method stub
       String input=c.element();
       String arr[]=input.split(",");
       c.output(KV.of(arr[0], Integer.valueOf(arr[3])));
	}
}

class KVToString extends DoFn<KV<String,Iterable<Integer>>,String>
{
	@ProcessElement
	private void processElement(ProcessContext c) {
		// TODO Auto-generated method stub
          String strkey=c.element().getKey();
          Iterable<Integer> val=c.element().getValue();
          
          Integer sum=0;
          for(Integer integer: val)
          {
        	  sum=sum+integer;
          }
          c.output(strkey+","+sum.toString());
	}
}

public class GroupByImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCustList=pipeline.apply(TextIO.read().from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\input.csv"));
		
		PCollection<KV<String,Integer>> kvOrder= pCustList.apply(ParDo.of(new StringToKV()));
		
		PCollection<KV<String,Iterable<Integer>>> kvOrder2= kvOrder.apply(GroupByKey.<String, Integer>create());
		
		PCollection<String> output=kvOrder2.apply(ParDo.of(new KVToString()));
		
		output.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\output.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();
	}

}
