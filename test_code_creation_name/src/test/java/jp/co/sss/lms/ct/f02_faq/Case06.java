package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		final WebElement faqLink = WebDriverUtils.webDriver
				.findElement(By.linkText("【研修関係】"));
		faqLink.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody span.text-primary.mr10")));

		final List<WebElement> targets = WebDriverUtils.webDriver
				.findElements(By.cssSelector("dt Span.text-primary.mr10+span"));

		List<String> faqTitles = new ArrayList<String>();
		;

		for (WebElement target : targets) {

			String faqTitle = target.getText();
			faqTitles.add(faqTitle);

		}

		assertTrue(faqTitles.contains("キャンセル料・途中退校について"));
		assertTrue(faqTitles.contains("研修の申し込みはどのようにすれば良いですか？"));
		assertFalse(faqTitles.contains("セルフ・キャリアドック制度とは何か"));
		assertFalse(faqTitles.contains("事業所が変わった場合、何かしら手続きをする必要がありますか？"));
		assertFalse(faqTitles.contains("助成金書類の作成方法が分かりません"));

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		final WebElement faqLink = WebDriverUtils.webDriver
				.findElement(By.cssSelector("tbody span.text-primary.mr10+span"));
		faqLink.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody span.text-warning.mr10+span")));

		final WebElement target = WebDriverUtils.webDriver
				.findElement(By.cssSelector("tbody span.text-warning.mr10+span"));
		String answer = target.getText();

		assertEquals(answer, "受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、"
				+ "事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

}
