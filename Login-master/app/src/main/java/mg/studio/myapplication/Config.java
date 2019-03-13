package mg.studio.myapplication;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * This class sets the URL to connect to the server
 *
 * Todo: https should de used instead of http
 */


public class Config {
    //Login
    public String URL_LOGIN = "https://mpianatra.com/Courses/api/login.php";

    //Register
    public String URL_REGISTER = "https://mpianatra.com/Courses/api/register.php";

    public String getLoginUrl() {
        return URL_LOGIN;
    }

    public String getRegisterUrl() {
        return URL_REGISTER;
    }
}
