class CommonPageJs {
    leftTopTitle = 'Order';
    topTitle = 'Home';
    rightSubTitle = 'Order/Home';
    loginUser = Util.user();

    constructor() {
        this.initPage();
    }

    initPage() {
        this.initUser();
    }

    initUser() {
        let loginUser = Util.user();
        if (loginUser !== null) {
            $('#icon-head').attr('src', loginUser.icon);
            $('#loginUser-name').text(loginUser.userName);
            $('#loginUser-role').text(loginUser.role);


        } else {

        }
    }
}