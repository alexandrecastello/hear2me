import os
from dotenv import load_dotenv
import openai
from googletrans import Translator

#load openai api key from .env file
load_dotenv()

openai.api_key = os.getenv('OPENAI_KEY')


def text_analysis(text):
    #function that takes an input text and use GPT-3 to summarize and do a sentiment analysis

    response = openai.Completion.create(
        engine="text-davinci-002",
        prompt=
        f"Summarize and return the sentiment of the following text:{text}",
        temperature=0.7,
        max_tokens=60,
        top_p=1.0,
        frequency_penalty=0.0,
        presence_penalty=0.0)
    answer = response.choices[0].text.strip()
    return answer


def translate(text):
    #Function that uses google translator to translate text to portuguese

    translator = Translator()

    result = translator.translate(text, dest='pt').text

    return result
