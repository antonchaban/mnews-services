from flask import Flask, request, jsonify
import pickle

app = Flask(__name__)
with open('model.pkl', 'rb') as f:
    model = pickle.load(f)

label_to_category = {
    0: "U.S. NEWS",
    1: "COMEDY",
    2: "PARENTING",
    3: "WORLD NEWS",
    4: "CULTURE & ARTS",
    5: "TECH",
    6: "SPORTS",
    7: "ENTERTAINMENT",
    8: "POLITICS",
    9: "WEIRD NEWS",
    10: "ENVIRONMENT",
    11: "EDUCATION",
    12: "CRIME",
    13: "SCIENCE",
    14: "WELLNESS",
    15: "BUSINESS",
    16: "STYLE & BEAUTY",
    17: "FOOD & DRINK",
    18: "MEDIA",
    19: "QUEER VOICES",
    20: "HOME & LIVING",
    21: "WOMEN",
    22: "BLACK VOICES",
    23: "TRAVEL",
    24: "MONEY",
    25: "RELIGION",
    26: "LATINO VOICES",
    27: "IMPACT",
    28: "WEDDINGS",
    29: "COLLEGE",
    30: "PARENTS",
    31: "ARTS & CULTURE",
    32: "STYLE",
    33: "GREEN",
    34: "TASTE",
    35: "HEALTHY LIVING",
    36: "THE WORLDPOST",
    37: "GOOD NEWS",
    38: "WORLDPOST",
    39: "FIFTY",
    40: "ARTS",
    41: "DIVORCE"
}


@app.route('/api/category-ai/predict', methods=['POST'])
def predict_category():
    data = request.get_json()
    text = data['text']
    category_label = model.predict([text])[0] - 1
    category_name = label_to_category.get(category_label, "Unknown")
    return jsonify({'category': category_name})


if __name__ == '__main__':
    app.run(debug=True)
