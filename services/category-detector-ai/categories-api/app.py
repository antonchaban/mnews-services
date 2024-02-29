from flask import Flask, request, jsonify
import pickle

app = Flask(__name__)
with open('model.pkl', 'rb') as f:
    model = pickle.load(f)

label_to_category = {
    0: "U_S_NEWS",
    1: "COMEDY",
    2: "PARENTING",
    3: "WORLD_NEWS",
    4: "CULTURE_AND_ARTS",
    5: "TECH",
    6: "SPORTS",
    7: "ENTERTAINMENT",
    8: "POLITICS",
    9: "WEIRD_NEWS",
    10: "ENVIRONMENT",
    11: "EDUCATION",
    12: "CRIME",
    13: "SCIENCE",
    14: "WELLNESS",
    15: "BUSINESS",
    16: "STYLE_AND_BEAUTY",
    17: "FOOD_AND_DRINK",
    18: "MEDIA",
    19: "QUEER_VOICES",
    20: "HOME_AND_LIVING",
    21: "WOMEN",
    22: "BLACK_VOICES",
    23: "GEOPOLITICS",
    24: "MONEY",
    25: "RELIGION",
    26: "LATINO_VOICES",
    27: "IMPACT",
    28: "WEDDINGS",
    29: "COLLEGE",
    30: "PARENTS",
    31: "ARTS_AND_CULTURE",
    32: "STYLE",
    33: "GREEN",
    34: "TASTE",
    35: "HEALTHY_LIVING",
    36: "THE_WORLDPOST",
    37: "GOOD_NEWS",
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
