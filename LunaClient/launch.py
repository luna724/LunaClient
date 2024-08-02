from flask import Flask, request

app = Flask(__name__)

@app.route('/receive_data', methods=['POST'])
def receive_data():
  data = request.json
  print("Received data:", data)
  return "Data received", 200

if __name__ == "__main__":
  # Port 9876 for receive data
  app.run(host='0.0.0.0', port=9876)