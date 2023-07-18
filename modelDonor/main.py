from flask import Flask,request,jsonify
import pickle
import numpy as np
model=pickle.load(open('finalized_model.sav','rb'))
app= Flask(__name__)
@app.route('/')
def home():
    return 'hello alaa'
@app.route('/predict',methods=['POST'])
def predict():
    Recency = request.form.get("Recency (months)")
    Frequency = request.form.get("Frequency (times)")
    Monetary = request.form.get("Monetary (c.c. blood)")
    Time = request.form.get("Time (months)")
    input=np.array([[Recency,Frequency,Monetary,Time]])
    result=model.predict(input)[0]
    print(result)

    return jsonify({'makeDonate':str(result)})

if __name__ == '__main__':
    app.run(host='192.168.1.7', port=8080)