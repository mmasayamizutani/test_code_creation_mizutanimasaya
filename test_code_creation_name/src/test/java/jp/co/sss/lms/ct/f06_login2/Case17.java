package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {

		final WebElement loginId = WebDriverUtils.webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA06");

		final WebElement loginPass = WebDriverUtils.webDriver.findElement(By.id("password"));
		loginPass.clear();
		loginPass.sendKeys("StudentAA06");

		final WebElement loginButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector("input[value='ログイン']"));
		loginButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("fieldset")));

		//		assertEquals(WebDriverUtils.webDriver.getTitle(), "セキュリティ規約 | LMS");
		//
		//		WebDriverUtils.getEvidence(new Object() {
		//		});
		//	}
		//
		//	@Test
		//	@Order(3)
		//	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
		//	void test03() {
		//
		//		((JavascriptExecutor) WebDriverUtils.webDriver)
		//				.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		//
		//		final WebElement agreeCheckBox = WebDriverUtils.webDriver
		//				.findElement(By.cssSelector("input[value='1']"));
		//		agreeCheckBox.click();
		//
		//		final WebElement nextButton = WebDriverUtils.webDriver
		//				.findElement(By.cssSelector("div button.btn.btn-primary"));
		//		nextButton.click();
		//
		//		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-group")));
		//
		//		assertEquals(WebDriverUtils.webDriver.getTitle(), "パスワード変更 | LMS");
		//
		//		WebDriverUtils.getEvidence(new Object() {
		//		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {

		final WebElement oldPass = WebDriverUtils.webDriver
				.findElement(By.xpath("//label[text()='現在のパスワード']/following-sibling::div//input[1]"));
		oldPass.clear();
		oldPass.sendKeys("StudentAA06");

		final WebElement newPass = WebDriverUtils.webDriver
				.findElement(By.xpath("//label[text()='新しいパスワード']//following-sibling::div//input[1]"));
		newPass.clear();
		newPass.sendKeys("StudentAA061");

		final WebElement refrainNewPass = WebDriverUtils.webDriver
				.findElement(By.xpath("//label[text()='確認パスワード']//following-sibling::div//input[1]"));
		refrainNewPass.clear();
		refrainNewPass.sendKeys("StudentAA061");

		final WebElement changeButton = WebDriverUtils.webDriver
				.findElement(By.xpath("//button[text()='変更']"));

		((JavascriptExecutor) WebDriverUtils.webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		changeButton.click();

		final WebDriverWait wait = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-footer")));

		final WebElement modalChangeButton = WebDriverUtils.webDriver
				.findElement(By.cssSelector(".modal-footer #upd-btn"));
		modalChangeButton.click();

		final WebDriverWait waitDetail = new WebDriverWait(WebDriverUtils.webDriver, Duration.ofSeconds(60));
		waitDetail.until(ExpectedConditions.visibilityOfElementLocated(By.className("container")));

		assertEquals(WebDriverUtils.webDriver.getTitle(), "コース詳細 | LMS");

		WebDriverUtils.getEvidence(new Object() {
		});

	}

}
