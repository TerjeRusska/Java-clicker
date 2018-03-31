package ee.ttu.java.cookie;

/**
 * Created by Terje on 03.04.2017.
 */
public class CookieGame {
    /**
     * Constant number.
     */
    public static final int TWENTY = 20;
    /**
     * Constant number.
     */
    public static final int HUNDRED = 100;
    /**
     * Constant number.
     */
    public static final int TWOHUNDRED = 200;
    /**
     * Constand number.
     */
    public static final int FIVEK = 5000;
    /**
     * Constant number.
     */
    public static final int THOUSAND = 1000;
    /**
     * Default cursor price.
     */
    private int cursorPrice = TWENTY;
    /**
     * Default cursor price increase.
     */
    private int cursorPriceIncrease = TWENTY;
    /**
     * Cursor counter.
     */
    private int cursorCount = 1;
    /**
     * Number of cookies.
     */
    private int cookies = 0;
    /**
     * Default clicker price.
     */
    private int clickerPrice = HUNDRED;
    /**
     * Default clicker price increase.
     */
    private int clickerPriceIncrease = TWOHUNDRED;
    /**
     * Default clicker interval.
     */
    private int clickerInterval = FIVEK;
    /**
     * Default clicker interval decrease.
     */
    private int clickerIntervalDecrease = HUNDRED;
    /**
     * Default clicker interval minimum.
     */
    private int clickerIntervalMin = THOUSAND;
    /**
     * Clicker counter.
     */
    private int clickerCount = 0;

    /**
     * Default constructor for game.
     */
    public CookieGame() {
    }

    /**
     * Constructor for game.
     * @param cursorPrice cursor price
     * @param cursorPriceIncrease cursor price increase
     */
    public CookieGame(int cursorPrice, int cursorPriceIncrease) {
        this.cursorPrice = cursorPrice;
        this.cursorPriceIncrease = cursorPriceIncrease;
    }

    /**
     * Constructor for complex game.
     * @param cursorPrice cursor price
     * @param cursorPriceIncrease cursor price increase
     * @param clickerPrice clicker price
     * @param clickerPriceIncrease clicker price increase
     * @param clickerInterval clicker interval
     * @param clickerIntervalDecrease clicker interval decrease
     * @param clickerIntervalMin clicker interval minimum
     */
    public CookieGame(int cursorPrice, int cursorPriceIncrease, int clickerPrice, int clickerPriceIncrease,
                      int clickerInterval, int clickerIntervalDecrease, int clickerIntervalMin) {
        this.cursorPrice = cursorPrice;
        this.cursorPriceIncrease = cursorPriceIncrease;
        this.clickerPrice = clickerPrice;
        this.clickerInterval = clickerInterval;
        this.clickerPriceIncrease = clickerPriceIncrease;
        this.clickerIntervalDecrease = clickerIntervalDecrease;
        this.clickerIntervalMin = clickerIntervalMin;
    }

    /**
     *Gets cursor price.
     * @return cursor price
     */
    public int getCursorPrice() {
        return this.cursorPrice;
    }

    /**
     * Gets the number of cookies.
     * @return number of cookies
     */
    public int getCookies() {
        return this.cookies;
    }

    /**
     * Gets cursor count.
     * @return cursor count
     */
    public int getCursorCount() {
        return this.cursorCount;
    }

    /**
     * Gets clicker count.
     * @return clicker count
     */
    public int getClickerCount() {
        return this.clickerCount;
    }

    /**
     * Gets clicker price.
     * @return clicker price
     */
    public int getClickerPrice() {
        return this.clickerPrice;
    }

    /**
     * Gets clicker interval.
     * @return clicker interval
     */
    public int getClickerInterval() {
        return this.clickerInterval;
    }

    /**
     * Clicker automatically clicks for cookies.
     */
    public void clickerAction() {
        if (this.clickerCount > 0) {
            this.cookies += this.cursorCount;
        }
    }

    /**
     * Cursor click gives cookies.
     */
    public void click() {
        this.cookies += this.cursorCount;
    }

    /**
     * Buys another cursor.
     */
    public void buyCursor() {
        if (canBuyCursor()) {
            this.cookies -= this.cursorPrice;
            this.cursorCount += 1;
            this.cursorPrice += this.cursorPriceIncrease;
        }
    }

    /**
     * Gets the minimum clicker interval.
     * @return minimum clicker interval
     */
    public int getClickerIntervalMin() {
        return this.clickerIntervalMin;
    }

    /**
     * Buys another clicker.
     */
    public void buyClicker() {
        if (canBuyClicker()) {
            this.cookies -= this.clickerPrice;
            this.clickerCount += 1;
            this.clickerPrice += this.clickerPriceIncrease;
            if (this.clickerCount != 1) {
                this.clickerInterval -= this.clickerIntervalDecrease;
                if (this.clickerInterval < this.clickerIntervalMin) {
                    this.clickerInterval = this.clickerIntervalMin;
                }
            }
        }
    }

    /**
     * Sees if a new cursor can be bought.
     * @return true false
     */
    public boolean canBuyCursor() {
        return this.cookies >= this.cursorPrice && this.cookies > 0 && this.cursorPrice > 0;
    }

    /**
     * Sees if a new clicker can be bought.
     * @return true false
     */
    public boolean canBuyClicker() {
        return this.cookies >= this.clickerPrice && this.cookies > 0 && this.clickerPrice > 0;
    }
}
