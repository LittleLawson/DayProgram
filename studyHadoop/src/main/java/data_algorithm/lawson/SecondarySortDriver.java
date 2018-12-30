package data_algorithm;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SecondarySortDriver extends Configured implements Tool {
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        Job job = new Job(conf);
        job.setJarByClass(SecondarySortDriver.class);
        job.setJobName("SecondSortedDriver");

        Path inputPath = new Path(args[0]);
        Path outPath = new Path(args[1]);

        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outPath);

        job.setOutputKeyClass(DateTemperaturePair.class);
        //对于这个为什么将输出值的类型设置成 NullWritable ?
        //job.setOutputValueClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(SecondarySortMapper.class);
        job.setReducerClass(SecondarySortReducer.class);

        //job.setPartitionerClass(DateTemperaturePartitioner.class);
        job.setGroupingComparatorClass(DateTemperatureGroupingComparator.class);

        boolean status = job.waitForCompletion(true);
        return status ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("exception...");
        }
        int returnStatus = ToolRunner.run(new SecondarySortDriver(), args);
        System.exit(returnStatus);
    }
}

/*

1. map阶段读入文本  -> keyIn:LongWritable  ; ValueIn:Text
2. map阶段输出文本  -> keyOut:Text   ; ValueOut: IntWritable
3. reduce阶段输入文本  -> keyOut:Text   ; ValueOut:IntWritable
4. reduce阶段输出文本 -> keyOut:Text   ; ValueOut:Text

输入文本：
2013,06,20,25
2013,06,23,34

输出文本：
2013-06 34,25

*/