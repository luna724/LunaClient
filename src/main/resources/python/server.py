from flask import Flask, request, jsonify
import os
import json
app = Flask(__name__)

# 保存先ディレクトリ
SAVE_DIR = os.path.join(os.getcwd(), "..\\")

@app.route('/autogarden/save_json', methods=['POST'])
def save_json():
    try:
        # リクエストボディからJSONを取得
        received_data = request.get_json()
        if not received_data:
            return jsonify({"error": "No JSON data received"}), 400

        # 必要なフィールドがあるか確認
        if 'fn' not in received_data or 'data' not in received_data:
            return jsonify({"error": "Missing 'fn' or 'data' field"}), 400

        # ファイル名とデータを取得
        file_name = received_data['fn']
        data_content = received_data['data']

        # ファイル名が安全か確認
        if not file_name.endswith('.json'):
            file_name += ".json"

        # 保存するファイルのパス
        file_path = os.path.join(SAVE_DIR, file_name)

        # JSONとして保存
        with open(file_path, 'w', encoding='utf-8') as json_file:
            json.dump(json.loads(data_content), json_file, ensure_ascii=False, indent=4)

        return jsonify({"message": "File saved successfully", "file_path": file_path}), 200
    except json.JSONDecodeError:
        return jsonify({"error": "Invalid JSON format in 'data'"}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3333)
