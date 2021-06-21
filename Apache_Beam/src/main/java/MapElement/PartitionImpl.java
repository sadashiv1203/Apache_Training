package MapElement;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Partition;
import org.apache.beam.sdk.transforms.Partition.PartitionFn;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;

class MyCityPartion implements PartitionFn<String>
{
	@Override
	public int partitionFor(String elem, int numPartitions) {
		// TODO Auto-generated method stub
		
		String arr[]=elem.split(",");
		
	    if(arr[3].equals("Los Angeles"))
	    {
	    	return 0;
	    }
	    else   if(arr[3].equals("Phoenix"))
	    {
	    	return 1;
	    }
	    else   
	    {
	    	return 2;
	    }
	    
		
	}
}

public class PartitionImpl 
{
	public static void main(String[] args) 
	{
        Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCust=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollectionList partitionList=pCust.apply(Partition.of(3, new MyCityPartion()));
		
		PCollection<String> p0=partitionList.get(0);
		PCollection<String> p1=partitionList.get(1);
		PCollection<String> p2=partitionList.get(2);
		
		p0.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\p0.csv").withNumShards(1).withSuffix(".csv"));
		p1.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\p1.csv").withNumShards(1).withSuffix(".csv"));
		p2.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\p2.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();
	}

}
