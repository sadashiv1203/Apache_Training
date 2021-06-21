package MapElement;

import java.util.Map;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

public class SideInputPardoImpl {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
        Pipeline pipeline=Pipeline.create();
		
		PCollection<KV<String,String>> pReturn=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"))
				.apply(ParDo.of(new DoFn<String, KV<String,String>>()
						{
					       @ProcessElement
					       private void proces(ProcessContext c) 
					       {
							// TODO Auto-generated method stub
					    	   String arr[]=c.element().split(",");
					    	   c.output(KV.of(arr[0],arr[1]));
					    	   

						   }
						}));
		
		PCollectionView<Map<String,String>> pMap=pReturn.apply(View.asMap());
		
		PCollection<String> custList=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\cust_Order.csv"));
		
		
		custList.apply(ParDo.of(new DoFn<String, Void>()
				{
			
			     @ProcessElement
		       private void proces(ProcessContext c) 
		       {
				// TODO Auto-generated method stub
		    	  Map<String,String> pSideInput=c.sideInput(pMap);
		    	 String arr[]= c.element().split(",");
		    	  
		    	  String custName=pSideInput.get(arr[0]);
		    	  
		    	  if(custName==null)
		    	  {
		    		  System.out.println(c.element());
		    	  }

			   }
			
				}).withSideInputs(pMap));
	
		
		

	}

}
