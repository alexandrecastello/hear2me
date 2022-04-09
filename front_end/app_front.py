import os
from flask import Flask, render_template, request
from random import randrange

app=Flask(__name__)

APP_ROOT=os.path.dirname(os.path.abspath(__file__))

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/upload",methods=['POST'])
def upload():
    target = os.path.join(APP_ROOT,'audios/')
    print(target)

    if not os.path.isdir(target):
        os.mkdir(target)

    for file in request.files.getlist("file"):
        #import pdb; pdb.set_trace()
        print(file)
        filename=file.filename
        #pdb.set_trace()
        namefile=filename.rsplit('.', 1)[0] + '_rand_' + str(randrange(1000000)) + '.' + filename.rsplit('.', 1)[1]
        #pdb.set_trace()
        destination = "/".join([target, namefile])
        print(destination)
        file.save(destination)

    return render_template("complete.html")

if __name__=="__main__":
    app.run(port=4555,debug=True)
