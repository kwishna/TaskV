package com.vlocity.qe;

import org.testng.annotations.DataProvider;

/**
 * Data Provider Class.
 *
 * Though A Single Method Is Enough For Provding Data To All The Tests By Passing {@link java.lang.reflect.Method} As Argument In Methods.
 * But, Here Separate Methods Are Used For Each Test.
 *
 * @author Krishna
 */
public class DataGenerator {

	public DataGenerator(){}

	@DataProvider(name = "dpApi")
	public Object[][] generateIndex(){

			return new Object[][]{
					{0},
					{1},
					{2},
					{3},
					{4},
					{5},
					{6},
					{7},
					{8},
					{9}
			};
	}

	@DataProvider(name = "languages")
	public Object[][] generateLanguages(){

		return new Object[][]{
				{"English"},
				{"Polski"},
				{"Español"},
				{"Русский"},
				{"Italiano"},
				{"Português"},
				{"中文"},
				{"Français"},
				{"Deutsch"},
				{"日本語"}
		};
	}
}
