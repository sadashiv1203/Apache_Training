package functions;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;

class LeftOrderParsing extends DoFn<String,KV<String,String>>
{
	@ProcessElement
	private void processElement(ProcessContext c) {
		// TODO Auto-generated method stub
		String arr[]=c.element().split(",");
		String strKey=arr[0];
		String strVal=arr[1]+","+arr[2]+","+arr[3];
		c.output(KV.of(strKey,strVal));

	}
}

class LeftUserParsing extends DoFn<String,KV<String,String>>
{
	@ProcessElement
	private void processElement(ProcessContext c) {
		String arr[]=c.element().split(",");
		String strKey=arr[0];
		String strVal=arr[1];
		c.output(KV.of(strKey,strVal));
	}
}

public class LeftJoinImpl {

	public static void main(String[] args) 
	{

		// TODO Auto-generated method stub
		
        Pipeline pipeline=Pipeline.create();
		
		PCollection<KV<String,String>> pOrderCollection=pipeline.apply(TextIO.read().from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\user_order.csv"))
				.apply(ParDo.of(new LeftOrderParsing()));
		
		PCollection<KV<String,String>> pUserCollection=pipeline.apply(TextIO.read().from("E:\\\\Spring_Microservices\\\\Apache Beam Practicals\\\\p_order.csv"))
				.apply(ParDo.of(new LeftUserParsing()));
		
		final TupleTag<String> orderTupple=new TupleTag<String>();
		final TupleTag<String> userTupple=new TupleTag<String>();
		
		PCollection<KV<String,CoGbkResult>> result= KeyedPCollectionTuple.of(orderTupple,pOrderCollection).and(userTupple, pUserCollection).apply(CoGroupByKey.<String>create());
		
		PCollection<String> output=result.apply(ParDo.of(new DoFn<KV<String,CoGbkResult>, String>()
					{
				       @ProcessElement
				       private void processElement(ProcessContext c) 
				       {
				         String strkey=c.element().getKey();
				         CoGbkResult valObject=c.element().getValue();
				         
				         Iterable<String> orderTable = valObject.getAll(orderTupple);
				         
				         Iterable<String> userTable = valObject.getAll(userTupple);
				         
				         for(String order: orderTable)
				         {
				        	 if(userTable.iterator().hasNext())
				        	 {
				        	  for(String user: userTable)
				        	  {
				        		  c.output(strkey+","+order+","+user);
				        	  }
				        	 }
				        	 else
				        	 {
				        		 c.output(strkey+","+order+","+null);
				        	 }
				         }
				       }
					}
			       ));
		
		output.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\left_join_example_output.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();

	
	}

}
