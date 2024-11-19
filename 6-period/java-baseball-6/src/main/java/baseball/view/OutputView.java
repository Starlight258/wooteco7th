package baseball.view;

public class OutputView {

    public void showStartMessage() {
        System.out.println("숫자 야구 게임을 시작합니다.");
    }

    public void showCommentForInputNumbers() {
        System.out.print("숫자를 입력해주세요 : ");
    }

    public void showResult(final String result) {
        System.out.println(result);
    }

    public void showEndMessage() {
        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
    }

    public void showCommentForRestart() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
    }
}