package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	private int port = 8080;

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		WebDriverUtils.goTo("http://localhost:" + port + "/lms");

		assertEquals(WebDriverUtils.webDriver.getTitle(), "ログイン | LMS");

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-group")));

		WebDriverUtils.getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		final WebElement loginId = WebDriverUtils.webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA03");

		final WebElement loginPass = WebDriverUtils.webDriver.findElement(By.id("password"));
		loginPass.clear();
		loginPass.sendKeys("StudentAA031");

		final WebElement loginButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[value='ログイン']"));
		loginButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contents")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "コース詳細 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		final WebElement detailButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("td input[value='2']+input[value='詳細']"));
		detailButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "セクション詳細 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		final WebElement reportRegistButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[value='提出済み週報【デモ】を確認する']"));
		reportRegistButton.click();

		//sectionServiceDailyReportDto

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("fieldset button.btn.btn-primary")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "レポート登録 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		final WebElement reportText = WebDriverUtils.webDriver
				.findElement(By.cssSelector("#content_1"));
		reportText.clear();
		reportText.sendKeys("テスト");

		final WebElement registButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("button.btn.btn-primary"));
		registButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "セクション詳細 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		final WebElement userName = WebDriverUtils.webDriver
				.findElement(By.linkText("ようこそ受講生ＡＡ３さん"));
		userName.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "ユーザー詳細");

		WebDriverUtils.getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しページ遷移")
	void test07() {

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		final WebElement detailButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[type='submit']:has(+ input[value='6'])"));
		detailButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "レポート詳細 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	//	@Test
	//	@Order(8)
	//	@DisplayName("テスト08 レポート詳細画面で修正内容が反映される")
	//	void test08() {
	//
	//		final String newReportText = WebDriverUtils.webDriver
	//				.findElement(By.xpath("//h3[text='報告レポート']/following-sibling::table[1]//td*[2]")).getText();
	//
	//		System.out.println(newReportText);
	//	}
	//	//.findElement(By.xpath("//h3[text='報告レポート']/following-sibling::table[1]//td"));
}
