import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;




public class TesteCampoTreinamento {
	
	private WebDriver driver;
	private DSL dsl;
	
	//metodo que se inicializa por primeiro	
	@Before
	public void inicializar() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
	}
	
	@After
	public void finalizar() {
		driver.quit();
	}
	
	@Test
	public void testeTextField() {	
		dsl.escreve("elementosForm:nome", "Teste de Escrita");
		Assert.assertEquals("Teste de Escrita", dsl.obterValorCampo("elementosForm:nome"));

	}
	
	@Test
	public void deveInteragirComTextArea() {
		dsl.escreve("elementosForm:sugestoes", "Ola Mundo Como Vai Voce\n\nEu Estou bem e voce");
		Assert.assertEquals("Ola Mundo Como Vai Voce\n\nEu Estou bem e voce", dsl.obterValorCampo("elementosForm:sugestoes"));
		//Assert.assertEquals("Ola Mundo Como Vai Voce", driver.findElement(By.id("elementosForm:sugestoes")).getAttribute("value"));

	}
	
	@Test
	public void deveInteragirComRadioButton() {
		dsl.clicarRadio("elementosForm:sexo:0");
		Assert.assertTrue(driver.findElement(By.id("elementosForm:sexo:0")).isSelected());

	}
	
	@Test
	public void deveInteragirComCheckBox() {
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
		Assert.assertTrue(dsl.isRadioMarcado("elementosForm:comidaFavorita:2"));

	}
	
	@Test
	public void deveInteragirComCombo() {		
		dsl.selecionarCombo("elementosForm:escolaridade", "2o grau incompleto");
		Assert.assertEquals("2o grau incompleto", dsl.obterValorCombo("elementosForm:escolaridade"));

	}
	
	@Test
	public void deveVerificarValoresCombo() {
		WebElement element = driver.findElement(By.id("elementosForm:escolaridade"));
		Select combo = new Select(element);
		List<WebElement> options =  combo.getOptions();
		Assert.assertEquals(8, options.size());
		
		boolean encontrou = false;
		for(WebElement option : options) {
			if(option.getText().equals("Mestrado")) {
				encontrou = true;
				break;
			}
		}
		
		Assert.assertTrue(encontrou);		

	}
	
	@Test
	public void deveVerificarValoresMultiplos() {
		dsl.selecionarCombo("elementosForm:esportes", "Natacao");
		dsl.selecionarCombo("elementosForm:esportes", "Corrida");
		dsl.selecionarCombo("elementosForm:esportes", "O que eh esporte?");
		
		WebElement element = driver.findElement(By.id("elementosForm:esportes"));
		Select combo = new Select(element);		
		
		List<WebElement> allSelectOption = combo.getAllSelectedOptions();
		Assert.assertEquals(3, allSelectOption.size());
		
		combo.deselectByVisibleText("Corrida");		
		allSelectOption = combo.getAllSelectedOptions();
		Assert.assertEquals(2, allSelectOption.size());
	
		
	}
	
	@Test
	public void DeveInteragirComBotoes() {
		dsl.clicarBotao("buttonSimple");
		
		WebElement botao =  driver.findElement(By.id("buttonSimple"));
		Assert.assertEquals("Obrigado!", botao.getAttribute("value"));
		
	}
	
	@Test
	public void DeveInteragirComLinks() {
		dsl.clicarLinks("Voltar");		
		Assert.assertEquals("Voltou!", dsl.obterTexto("resultado"));

	}
	
	@Test
	public void deveBuscarTextosNaPagina() {
		//driver.findElement(By.tagName("body")).getText().contains("Campo de Treinamento");		
		Assert.assertEquals("Campo de Treinamento", dsl.obterTexto(By.tagName("h3")));		
		Assert.assertEquals("Cuidado onde clica, muitas armadilhas...", dsl.obterTexto(By.className("facilAchar")));

	}	
	

}
