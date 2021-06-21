package ConnectionExamples;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.hdfs.HadoopFileSystemOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.hadoop.conf.Configuration;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class HdfcImpl {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		Configuration conf=new Configuration();
		
		conf.set("fs.defaultFS", "hdfs://172.31.26.230:8020");
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl",org.apache.hadoop.fs.LocalFileSystem.class.getName());
		
		String[] args1=new String[] {"--hdfsConfiguration=[{\"fs..default.name\" : \"hdfs://172.31.26.230:8020\"}]",
				"--runner=DirectRunner"};
		
		HadoopFileSystemOptions hdfsOption=PipelineOptionsFactory.fromArgs(args).withValidation().as(HadoopFileSystemOptions.class);
		hdfsOption.setHdfsConfiguration(Collections.singletonList(conf));
		
		Pipeline pipeline=Pipeline.create(hdfsOption);
		
		PCollection<String> pHdfs =pipeline.apply(TextIO.read().from("hdfs://172.31.26.230:8020/user.user.csv"));
		pHdfs.apply(ParDo.of(new DoFn<String, Void>()
				{
			        @ProcessElement
			        public void processElement(ProcessContext c)
			        {
			        	System.out.println(c.element());
			        }
			
				}));
		pipeline.run();
		

	}

}
