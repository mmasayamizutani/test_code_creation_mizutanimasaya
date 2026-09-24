package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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

}
