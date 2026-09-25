package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		final WebElement dropdown = WebDriverUtils.webDriver
				.findElement(By.className("dropdown"));
		dropdown.click();

		final WebElement helpLink = WebDriverUtils.webDriver
				.findElement(By.linkText("ヘルプ"));
		helpLink.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("panel-title")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "ヘルプ | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		final WebElement faq = WebDriverUtils.webDriver
				.findElement(By.linkText("よくある質問"));
		faq.click();

		String originalWindow = WebDriverUtils.webDriver.getWindowHandle();

		for (String windowHandle : WebDriverUtils.webDriver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				WebDriverUtils.webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("container")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "よくある質問 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		final WebElement serchWord = WebDriverUtils.webDriver.findElement(By.id("form"));
		serchWord.clear();
		serchWord.sendKeys("事業所");

		final WebElement serchButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[value='検索']"));
		serchButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className("odd")));

		final WebElement target = WebDriverUtils.webDriver
				.findElement(By.cssSelector("dt Span.text-primary.mr10+span"));

		String title = target.getText();
		assertTrue(title.contains("事業所"));

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		//		JavascriptExecutor js = (JavascriptExecutor) WebDriverUtils.webDriver;
		//		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0,0);");

		final WebElement clearButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[value='クリア']"));
		clearButton.click();

		final WebElement target = WebDriverUtils.webDriver
				.findElement(By.id("form"));
		String serchWord = target.getText();

		assertEquals(serchWord, "");
	}

}
