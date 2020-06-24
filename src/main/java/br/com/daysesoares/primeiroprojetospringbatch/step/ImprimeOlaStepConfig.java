package br.com.daysesoares.primeiroprojetospringbatch.step;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImprimeOlaStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step imprimeOlaStep(Tasklet imprineOlaTasklet) {
		return stepBuilderFactory
				.get("imprimeOlaStep")
				.tasklet(imprineOlaTasklet)
				.build();
	}
	
	public Step imprimeParImparStep() {
		return stepBuilderFactory
				.get("imprimeParImparStep")
				.<Integer, String>chunk(1)
				.reader(contaAteDezReader())
				.processor(parOuImparProcessor())
				.writer(imprimeWriter())
				.build();
	}
	
	public IteratorItemReader<Integer> contaAteDezReader() {
		List<Integer> numeroDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		return new IteratorItemReader<Integer>(numeroDeUmAteDez.iterator());
	}
	
	public FunctionItemProcessor<Integer, String> parOuImparProcessor(){
		return new FunctionItemProcessor<Integer, String>
			(item -> item % 2 == 0 ? String.format("Item %s é par.", item) :
				String.format("Item %s é impar.", item));
	}
	
	public ItemWriter<String> imprimeWriter(){
		return itens -> itens.forEach(System.out::println);
	}
	
}
