# -*- coding: utf-8 -*-
from flask import Flask
app = Flask(__name__)


content = '''
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<script>
var xhr = new XMLHttpRequest();

// 指定通信过程中状态改变时的回调函数
xhr.onreadystatechange = function(){
  // 通信成功时，状态值为4
  if (xhr.readyState === 4){
    if (xhr.status === 200){
        document.body.innerHTML = xhr.responseText
    } else {
        document.body.innerHTML = xhr.statusText
    }
  }
};

xhr.onerror = function (e) {
  console.error(xhr.statusText);
};

// open方式用于指定HTTP动词、请求的网址、是否异步
xhr.open('GET', 'http://127.0.0.1:4000/get_data', true);

// 发送HTTP请求
xhr.send(null);
</script>
</body>
</html>
'''


@app.route("/")
def hello():
    return content


@app.route('/get_data')
def get_data():
    return 'got data!'


if __name__ == '__main__':
    app.run(port=4000)