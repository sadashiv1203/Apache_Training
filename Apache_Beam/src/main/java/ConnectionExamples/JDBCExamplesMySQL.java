package ConnectionExamples;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

public class JDBCExamplesMySQL {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		// install mysql workbench
		
		Pipeline pipeline=Pipeline.create();
		
		PCollection<String> pCollection= pipeline.apply(JdbcIO.<String>read().withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create("driver class","database url").withUsername("username").withPassword("password"))
				.withQuery("select name,city,currency from product_info where name = ?").withCoder(StringUtf8Coder.of())
				.withStatementPreparator(new JdbcIO.StatementPreparator() {
					
					@Override
					public void setParameters(PreparedStatement preparedStatement) throws Exception {
						// TODO Auto-generated method stub
						
						preparedStatement.setString(1,"iphone");
						
					}
				}).withRowMapper(new JdbcIO.RowMapper<String>() 
				{

					@Override
					public String mapRow(ResultSet resultSet) throws Exception {
						// TODO Auto-generated method stub
						return resultSet.getString(1)+","+resultSet.getString(2)+","+resultSet.getString(3);
					}
					
				})
				);
		
		pCollection.apply(TextIO.write().to("E:\\Spring_Microservices\\Apache Beam Practicals\\database_output.csv").withNumShards(1).withSuffix(".csv"));
		
		pipeline.run();

	}

}
