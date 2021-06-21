package apache.beam.demo;

import java.util.stream.Collectors;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.SqlTransform;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TupleTag;

import apache.beam.demo.GroupByExample.RowToString;
import apache.beam.demo.GroupByExample.StringToRow;

public class BeamJoinExamples {



	final static String order_Header="userId,orderId,productId,Amount";
	final static Schema order_schema= Schema.builder().addStringField("UserId")
			.addStringField("OrderId").addStringField("ProductId").addStringField("Amount").build();
	
	final static String user_Header="userId,userName";
	final static Schema user_schema= Schema.builder().addStringField("UserId")
			.addStringField("userName").build();
	
	final static Schema order_user_schema= Schema.builder().addStringField("UserId")
			.addStringField("OrderId").addStringField("ProductId").addStringField("Amount").addStringField("userName").build();
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> order=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\user_order.csv"));
		PCollection<String> user=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\user_deatils.csv"));
		
		PCollection<Row> OrderRowInput=order.apply(ParDo.of(new StringToOrderRow())).setRowSchema(order_schema);
		PCollection<Row> UserRowInput=user.apply(ParDo.of(new StringToUserRow())).setRowSchema(user_schema);
		
		PCollection<Row> sqlInput=PCollectionTuple.of(new TupleTag<>("orders"),OrderRowInput)
		                   .and(new TupleTag<>("users"),UserRowInput)
		                   .apply(SqlTransform.query("sleect o.*,u.name from orders o inner join users on a.userId=u.userId"));
		                   
		PCollection<String> output=sqlInput.apply(ParDo.of(new RowToString()));
		
		 output.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\sql_join_output.csv").withNumShards(1).withSuffix(".csv"));
	        
		 pipeline.run();

	}
	
	public static class  StringToOrderRow extends DoFn<String, Row>
	{
		@ProcessElement
		public void processElement(ProcessContext c) 
		{
			if(c.element().equalsIgnoreCase(order_Header))
			{
				String arr[]=c.element().split(",");
				Row record=Row.withSchema(order_schema).addValues(arr[0],arr[1],arr[2],arr[3]).build();
				c.output(record);
			}

		}
	}
	
	public static class  StringToUserRow extends DoFn<String, Row>
	{
		@ProcessElement
		public void processElement(ProcessContext c) 
		{
			if(c.element().equalsIgnoreCase(user_Header))
			{
				String arr[]=c.element().split(",");
				Row record=Row.withSchema(user_schema).addValues(arr[0],arr[1]).build();
				c.output(record);
			}

		}
	}
	
	public static class  RowToString extends DoFn<Row,String>
	{
		@ProcessElement
		public void processElement(ProcessContext c) 
		{
			String output=c.element().getValues().stream().map(Object::toString).collect(Collectors.joining(","));
			c.output(output);
		}
	}



}
