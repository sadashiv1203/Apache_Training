package apache.beam.demo.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

public class ReadInMemoryData {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		/*Pipeline pipeline=Pipeline.create();
		
		PCollection<Customer> pList=pipeline.apply(Create.of(getCustomers()));
		
		PCollection<String> pStrList=pList.apply(MapElements.into(TypeDescriptors.strings()).via((Customer cust)-> cust.getName()));
		
		pStrList.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\Customer_output.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();*/

	}
	
	static List<Customer> getCustomers()
	{
		List<Customer> list=new ArrayList<>();
		Customer c1=new Customer("1","Sadashiv");
		Customer c2=new Customer("2","Suryaji");
		list.add(c1);
		list.add(c2);
		return list;
	}

}
