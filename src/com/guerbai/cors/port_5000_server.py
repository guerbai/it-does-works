# -*- coding: utf-8 -*-
from flask import Flask
# from flask_cors import CORS
app = Flask(__name__)
# CORS(app)

@app.route("/get_data_5000")
def hello():
    return "got data in 5000!"


if __name__ == '__main__':
    app.run(port=5000)
