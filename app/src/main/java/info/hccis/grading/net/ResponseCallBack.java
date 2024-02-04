package info.hccis.grading.net;
/**
 * Callback functionality for use with api requests to ensure code execution after server response
 *
 * @author David Deschene <ddeschene@hollandcollege.com>
 * @since Jan 22, 2024
 */
public interface ResponseCallBack {
    void onSuccess();
    void onError();

}
