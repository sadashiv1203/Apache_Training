package beam.sql;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.SqlTransform;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;



public class SQLExample {

	final static String HEADER="userId,orderId,productId,Amount";
	final static Schema schema= Schema.builder().addStringField("UserId")
			.addStringField("OrderId").addStringField("ProductId").addStringField("Amount").build();
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCollection=pipeline.apply(TextIO.read().from("E:\\Spring_Microservices\\Apache Beam Practicals\\input.csv"));
		
		PCollection<Row> pRowInput=pCollection.apply(ParDo.of(new StringToRow())).setRowSchema(schema);
		
		PCollection<Row> sqlInput=pRowInput.apply(SqlTransform.query("select * from PCOLLECTION"));
		
		PCollection<String> output=sqlInput.apply(ParDo.of(new RowToString()));
		
		 output.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\output.csv").withNumShards(1).withSuffix(".csv"));
	        
		 pipeline.run();

	}
	
	public static class  StringToRow extends DoFn<String, Row>
	{
		@ProcessElement
		public void processElement(ProcessContext c) 
		{
			if(c.element().equalsIgnoreCase(HEADER))
			{
				String arr[]=c.element().split(",");
				Row record=Row.withSchema(schema).addValues(arr[0],arr[1],arr[2],arr[3]).build();
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
