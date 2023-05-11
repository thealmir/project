let css_light = 'html { filter: invert(0%); }';
    css_dark  = 'html { filter: invert(100%); }';

window.onload = function () {
    let cookies = document.cookie.split('; ');
    for (let i in cookies) {
        if (cookies[i].split('=')[0] === 'color-scheme') {
            if (cookies[i].split('=')[1] === 'dark') {
                reverseColors(css_dark);
            } else {
                reverseColors(css_light);
            }
        }
    }
}

function changeCookie() {
    let cookies = document.cookie.split('; ');
    let flag = false;
    for (let i in cookies) {
        if (cookies[i].split('=')[0] === 'color-scheme') {
            if (cookies[i].split('=')[1] === 'dark') {
                reverseColors(css_light);
                document.cookie = 'color-scheme=light; max-age=3600';
            } else {
                reverseColors(css_dark);
                document.cookie = 'color-scheme=dark; max-age=3600';
            }
            flag = true;
        }
    }

    if (!flag) {
        reverseColors(css_dark);
        document.cookie = 'color-scheme=dark; max-age=3600';
    }
}

document.addEventListener("click", (e) => {
    if (e.target.className === "modal-open item_1") {
        changeCookie();
    }
});

function reverseColors(colors) {

    let head = document.getElementsByTagName('head')[0];
    let style = document.createElement('style');

    style.appendChild(document.createTextNode(colors));
    head.appendChild(style);

}