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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		final WebElement detailButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("td input.btn.btn-default"));
		detailButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "セクション詳細 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		final WebElement reportRegistButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[value='日報【デモ】を提出する']"));
		reportRegistButton.click();

		//sectionServiceDailyReportDto

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("fieldset button.btn.btn-primary")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "レポート登録 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}
	//
	//	@Test
	//	@Order(5)
	//	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	//	void test05() {
	//
	//		final WebElement reportText = WebDriverUtils.webDriver.findElement(By.tagName("textarea"));
	//		reportText.clear();
	//		reportText.sendKeys("テスト");
	//
	//		final WebElement registCompleteButton = WebDriverUtils.webDriver
	//				.findElement(By.cssSelector("fieldset button.btn.btn-primary"));
	//		registCompleteButton.click();
	//		
	//		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
	//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table")));
	//
	//		final WebElement target = WebDriverUtils.webDriver
	//				.findElement(By.cssSelector("         "));
	//		String buttonName = target.getText();
	//		
	//		
	//		assertEquals(buttonName,"提出済み日報【デモ】を確認する");
	//		
	//		
	//		WebDriverUtils.getEvidence(new Object() {
	//		});
	//	}

}
