class Util {
    static PORTAL = 'http';
    static HOST = "localhost"
    static PORT = "9090";
    static url = function (api) {
        return Util.PORTAL + '://' + (Util.PORT == null || Util.PORT.trim() === '' ? '' : Util.HOST + ':') + Util.PORT +
            (api[0] === '/' ? api : '/' + api);
    }

    static restfulUrl(api, lets) {
        let url = Util.url(api);
        if (url[url.length - 1] !== '/') {
            url += '/';
        }
        if (lets.length > 0) {
            url += lets[0];
            for (let i = 1; i < lets.length; i++) {
                url += '/' + lets[i];
            }
        }
        return url;
    }

    static origin() {
        return Util.PORTAL + '://' + Util.PORT;
    }

    static dateFormat(dateStr) {
        let date = new Date(dateStr);
        if (date == 'Invalid Date') {
            return null;
        }
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }

    static datetimeFormat(dateStr) {
        let date = new Date(dateStr);
        if (date == 'Invalid Date') {
            return null;
        }
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() +
            ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    }

    static timeFormat(dateStr) {
        let date = new Date(dateStr);
        if (date == 'Invalid Date') {
            return null;
        }
        return date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    }

    static user() {
        return  Util.getUser();
    }

    /**
     * 0 visitor 1 customer 2 merchant 3 administrator
     * @param i
     * @returns {*}
     */
    static getUser(i) {
        if (typeof (i) !== 'number') {
            let loginUser = null;
            $.get({
                async: false,
                url: Util.url('user/loginUser'),
                success: function (json) {
                    if (json.code === 0) {
                        loginUser = json.data;
                    }
                }
            });
            console.log(loginUser);
            return loginUser;
        }
        switch (i) {
            case 0:
                return null;
            case 1:
                return {
                    "id": 1,
                    "email": "1@1.com",
                    "dob": "2000-11-13",
                    "userName": "Bob",
                    "icon": "https://tse4-mm.cn.bing.net/th/id/OIP-C.oRG0873dtD9R0swgXDrKcwHaF7?w=196&h=180&c=7&r=0&o=5&dpr=1.6&pid=1.7",
                    "address": "BeiJing",
                    "paymentInfo": 950.5,
                    "role": "Customer"
                };
            case 2:
                return {
                    "id": 3,
                    "email": "3@3.com",
                    "dob": "1999-06-01",
                    "userName": "BB",
                    "icon": "https://csdnimg.cn/release/blogv2/dist/pc/img/npsFeel5.png",
                    "address": "ChongQing",
                    "paymentInfo": 999999.0,
                    "role": "Merchant"
                };
            case 3:
                return {
                    "id": 2,
                    "email": "2@2.com",
                    "dob": "2000-08-13",
                    "userName": "AAA",
                    "icon": "https://csdnimg.cn/release/blogv2/dist/pc/img/npsFeel1.png",
                    "address": "ChangSha",
                    "paymentInfo": 5000.0,
                    "role": "Administrator"
                };
            case 4:
                return {
                    "id": 23,
                    "email": "merchant@merchant.com",
                    "dob": "1999-07-22",
                    "userName": "merchant",
                    "icon": "",
                    "address": "12E, 575 Easton Ave",
                    "paymentInfo": 500,
                    "role": "Merchant"
                }
            default:
                return null;
        }
    }


    static emptyToNull(val) {
        return val === null || val === undefined || val === "" ? null : val;
    }

    static emptyToNullTrim(val) {
        return val === null || val === undefined || val.trim !== undefined && val.trim() === "" ? null : val;
    }

    static jqEq(selector, pos) {
        return $(selector + ':eq(' + pos + ')');
    }

    // static setText(div, text) {
    //     let deleteDom = [].slice.apply(div[0].childNodes).filter(function (item) {
    //         return item.nodeType === 3;
    //     });
    //     div.contents().filter(function(){
    //         for (let i = 0; i < deleteDom.length; i++) {
    //             if (deleteDom[i] === this) {
    //                 return true;
    //             }
    //         }
    //         return false;
    //     }).remove();
    //
    //     div.prepend(document.createTextNode(text));
    // }

    static number(val) {
        if (val === null || val === undefined) {
            return null;
        }
        let type = typeof(val);
        if (type === 'number') {
            return val;
        }
        if (type === 'string') {
            if (val.trim() === '') {
                return null;
            }
            return Number(val);
        }
        return null;
    }

    static bool(val) {
        if (val === null || val === undefined) {
            return null;
        }
        let type = typeof(val);
        if (type === 'boolean') {
            return val;
        }
        if (type === 'number') {
            return Boolean(val);
        }
        if (type === 'string') {
            if (val.trim().toLowerCase() === 'false') {
                return false;
            }
            return true;
        }
        return null;
    }

    /**
     * 弹出消息框
     * @param msg 消息内容
     * @param param 消息框类型（参考bootstrap的alert）| 颜色 | 状态码
     *
     * type: 类型或者背景颜色
     *  primary     :   浅蓝色
     *  secondary   :   浅灰色
     *  success     :   绿色[默认]
     *  danger      :   粉红
     *  warning     :   黄色
     *  info        :   深蓝色
     *  light       :   亮灰色(接近白色)
     *  dark        :   暗灰色
     */
    static alert = function(msg, param){
        let type = param;
        let colorStyle = '';
        if(typeof(param) =="undefined") { // 未传入type则默认为success类型的消息框
            type = "success";
        } else if (typeof(param) == "string" && (param.startsWith('rgb') || param.startsWith('RGB') || param.startsWith('#'))) {
            colorStyle = 'background-color: ' + param + ';';
            type = "success";
        } else if (typeof(param) == "number") {
            if (param === 0 || param === 200) {
                type = "success";
            } else if (param === 1 || param === 500) {
                type = "danger";
            } else {
                type = "warning";
            }
        }
        // 创建bootstrap的alert元素
        let divElement = $("<div style='margin-left: 10%;margin-right: 10%;"+ colorStyle +"'></div>").addClass('alert').addClass('alert-'+type).addClass('alert-dismissible').addClass('col-md-10');
        divElement.css({ // 消息框的定位样式
            "position": "absolute",
            "top": "80px"
        });
        divElement.text(msg); // 设置消息框的内容
        // 消息框添加可以关闭按钮
        let closeBtn = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>');
        $(divElement).append(closeBtn);
        // 消息框放入到页面中
        $('body').append(divElement);
        return divElement;
    }

    /**
     * 短暂显示后上浮消失的消息框
     * @param msg 消息内容
     * @param type 消息框类型
     */
    static message = function(msg, type) {
        let divElement = Util.alert(msg, type); // 生成Alert消息框
        let isIn = false; // 鼠标是否在消息框中

        divElement.on({ // 在setTimeout执行之前先判定鼠标是否在消息框中
            mouseover : function(){isIn = true;},
            mouseout  : function(){isIn = false;}
        });

        // 短暂延时后上浮消失
        setTimeout(function() {
            let IntervalMS = 20; // 每次上浮的间隔毫秒
            let floatSpace = 60; // 上浮的空间(px)
            let nowTop = divElement.offset().top; // 获取元素当前的top值
            let stopTop = nowTop - floatSpace;    // 上浮停止时的top值
            divElement.fadeOut(IntervalMS * floatSpace); // 设置元素淡出

            let upFloat = setInterval(function(){ // 开始上浮
                if (nowTop >= stopTop) { // 判断当前消息框top是否还在可上升的范围内
                    divElement.css({"top": nowTop--}); // 消息框的top上升1px
                } else {
                    clearInterval(upFloat); // 关闭上浮
                    divElement.remove();    // 移除元素
                }
            }, IntervalMS);

            if (isIn) { // 如果鼠标在setTimeout之前已经放在的消息框中，则停止上浮
                clearInterval(upFloat);
                divElement.stop();
            }

            divElement.hover(function() { // 鼠标悬浮时停止上浮和淡出效果，过后恢复
                clearInterval(upFloat);
                divElement.stop();
            },function() {
                divElement.fadeOut(IntervalMS * (nowTop - stopTop)); // 这里设置元素淡出的时间应该为：间隔毫秒*剩余可以上浮空间
                upFloat = setInterval(function(){ // 继续上浮
                    if (nowTop >= stopTop) {
                        divElement.css({"top": nowTop--});
                    } else {
                        clearInterval(upFloat); // 关闭上浮
                        divElement.remove();    // 移除元素
                    }
                }, IntervalMS);
            });
        }, 1500);
    }

    static info(msg) {
        this.message(msg, "info");
    }

    static warning(msg) {
        this.message(msg, "warning");
    }

    static error(msg) {
        this.message(msg, "danger")
    }

    static success(msg) {
        this.message(msg, "success");
    }
}

class UiUtilItem {
    constructor(params) {
        console.log(params);

        this.setPos(params.pos);

        this.setRoot(params.root);

        this.setData(params.data);

        this.init();
    }

    setPos(pos) {
        this.pos = pos === undefined ? 0 : pos;
    }

    setRoot(root) {
        this.root = root;
        this.ui = root.ui.children(this.pos);
    }

    setData(data) {
        if (data === undefined) {
            this.data = this.root.data[this.pos];
        } else {
            this.data = data;
        }
    }

    init() {
        let item = this;

        this.components = new Map();
        this.root.componentsKeys.forEach(function (key) {
            item.components.set(key.substr(item.root.classPrefix.length), item.ui.find('.' + key).eq(item.pos));
        })

        this.updateUI();
    }

    updateUI(data) {
        if (data !== undefined) {
            this.setData(data);
        }
        let item = this;
        if (this.root.match !== undefined) {
            this.components.forEach(function (component, key) {
                let m = item.root.match[key];
                if (m instanceof Function) {
                    m(item.data, item, component);
                } else {
                    if (m === undefined) {
                        m = {}
                    }
                    let textType = m['textType'] === undefined ? item.root.textType : m['textType'];
                    let text = m['dataColum'] === undefined ? m.text === undefined ? item.data[key] : m.text : item.data[m['dataColum']];
                    if (text !== undefined && typeof(textType) === 'string') {
                        if (text == null) {
                            text = '';
                        } else {
                            text = String(text);
                        }
                        if (textType === "val") {
                            component.val(text);
                        } else if (textType === 'text') {
                            component.text(text);
                        } else if(textType === 'html') {
                            component.html(text);
                        } else if (textType === 'src') {
                            component.attr('src', text);
                        }  else if (textType === 'radio') {
                            let name = item.name === undefined ? key : item.name;
                            let radios = item.ui.find('input[name="'+ name +'"]');
                            radios.attr('checked', null);
                            let d = item.data[key];
                            for (let i = 0; i < radios.length; i++) {
                                let r = radios.eq(i);
                                let v = r.val();
                                if (v === d) {
                                    r.attr('checked', 'checked');
                                    break;
                                }
                            }
                        } else {

                        }
                    }

                    if (m['click'] instanceof Function) {
                        component.click(function () {
                            m['click'](item.data, item, component);
                        });
                    }

                    if (m['template'] instanceof Function) {
                        m['template'](item.data, item, component);
                    }
                }

            });
        }
    }

    /**
     * 从UI中读取数据，生成data [注意只获取components的在match中对应的值，并按照键值对存放]
     * @returns {*}
     */
    getDataFromUI() {
        let data = {};
        let item = this;
        this.components.forEach(function (component, key) {
            let m = item.root.match[key];
            if (m instanceof Function) {
                return;
            }
            let textType = m === undefined || m['textType'] === undefined ? item.root.textType : m['textType'];
            if (typeof(textType) !== 'string') {
                return;
            }
            let d = null;
            if (textType === "val") {
                d = component.val();
            } else if (textType === 'text') {
                d = component.text();
            } else if(textType === 'html') {
                d = component.html();
            } else if (textType === 'src') {
                d = component.attr('src');
            } else if (textType === 'radio') {
                let name = item.name === undefined ? key : item.name;
                let radios = item.ui.find('input[name="'+ name +'"]');
                for (let i = 0; i < radios.length; i++) {
                    let r = radios.eq(i);
                    let v = r.val();
                    if (r.attr('checked') !== undefined) {
                        d = v;
                        break;
                    }
                }
            } else {

            }
            d = Util.emptyToNull(d);
            data[key] = d;
        })
        return data;
    }
}

class UiUtil {
    /*
    match:{
        key1 : {
            text: <text>, // text 与 dataColumn 只能存在一个[dataColumn会覆盖text]
            dataColumn : <dataColumn>,
            textType: <text(默认)|val|html|src|radio>,
            click : <function(data, item, component)>,
            template: <function(data, item, component)>
        },
        key2 : <function(data, item, component)>,
   }
     */

    /**
     * match *,
     * rootID *,
     * data ,
     * classPrefix(默认为 'component-' ),
     * templateHtml(默认读取当前html),
     * loadFun:function(uiUtil),
     * preLoad:ture(默认)|false,
     * textType: text(默认)|val|html
     * @param params
     */
    constructor(params) {
        this.setRoot(params.rootID);

        this.setData(params.data);

        this.setClassPrefix(params.classPrefix);

        this.setTemplateHtml(params.templateHtml);

        this.setMatch(params.match);

        this.setTextType(params.textType);

        this.setLoadFun(params.loadFun);

        this.preLoad = (params['preLoad'] === undefined || params['preLoad'] === true);
        if (this.preLoad) {
            this.load();
        }
    }

    setData(data) {
        if (data === undefined) {
            return;
        }

        if (data instanceof Array) {
            this.data = data;
        } else {
            this.data = [data]
        }
    }

    setClassPrefix(classPrefix) {
        this.classPrefix = classPrefix === undefined ? 'component-' : classPrefix;
    }

    setRoot(rootID) {
        if (rootID === undefined) {
            return
        }
        this.rootID = rootID;
        this.ui = $('#' + rootID);
    }


    load() {
        if (this.loadFun instanceof Function) {
            this.loadFun(this);
        } else {
            this.updateUI();
        }
    }

    setTemplateHtml(templateHtml) {
        this.templateHtml = templateHtml === undefined ? this.ui.html() : templateHtml;
        this.ui.html(templateHtml);
        let template = this.ui.children(0).find('*');
        this.componentsKeys = new Set();
        for (let i = 0; i < template.length; i++) {
            let classAttr = template.eq(i).attr('class');
            if (classAttr === undefined) {
                continue;
            }
            let cs = classAttr.split(' ');
            for (let j = 0; j < cs.length; j++) {
                let cla = cs[j];
                if (cla.startsWith(this.classPrefix)) {
                    this.componentsKeys.add(cla);
                }
            }
        }
        this.ui.html('')
    }

    setMatch(match) {
        this.match = match;
    }

    updateUI(data) {
        if (data !== undefined) {
            this.setData(data);
        }
        this.children = [];
        if (this.data !== undefined) {
            this.ui.html(this.templateHtml.repeat(this.data.length));
            for (let i = 0; i < this.data.length; i++) {
                let d = this.data[i];
                this.children[i] = new UiUtilItem({
                    root: this,
                    data: d,
                    pos: i
                });
            }
        }
    }

    setLoadFun(loadFun) {
        if (loadFun instanceof Function) {
            this.loadFun = loadFun;
        }
    }

    setTextType(textType) {
        if (textType !== 'val' && textType !== 'html') {
            this.textType = 'text';
        } else {
            this.textType = textType;
        }
    }
}