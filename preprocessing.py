# from nltk.corpus import stopwords
# from nltk.tokenize import word_tokenize
from pydub import AudioSegment
from urllib import request
import math
import joblib
import os
from pydub.effects import normalize


def convert_audio(audio_path, filename):
    local_file = filename
    request.urlretrieve(audio_path, local_file)
    sound = AudioSegment.from_ogg(local_file)
    sound = sound.set_frame_rate(16000)
    sound = normalize(sound)
    audiolen = 60
    i = 0
    text = ""
    probsum = []
    problen = []
    subitems = math.ceil(len(sound) / 60000 * 60 / audiolen)

    if (subitems > 0):
        for item in range(0, subitems):
            if (item + 1 == math.ceil(len(sound) / 60000 * 60 / 50)):
                start = i * audiolen * 1000
                end = len(sound) / 60000 * 60 * 1000
            else:
                start = audiolen * i * 1000
                end = audiolen * (i + 1) * 1000
            sound[start:end].export(f"{filename[:-4]}_{i}.wav", format="wav")
            i += 1
    else:
        sound[start:end].export(f"{filename[:-4]}.wav", format="wav")

    model = joblib.load('model_j.joblib')

    i = 0
    if (subitems > 0):
        for item in range(0, subitems):
            #             if(i!=0 & i%4==0):
            #                 del model
            #                 gc.collect()
            #                 model=joblib.load('model.joblib')

            transcription = model.transcribe([f"{filename[:-4]}_{i}.wav"])
            text += transcription[0]['transcription']
            probsum.append(sum(transcription[0]['probabilities']))
            problen.append(len(transcription[0]['probabilities']))
            os.remove(f"{filename[:-4]}_{i}.wav")
            i += 1
    else:
        transcription = model.transcribe([f"{filename[:-4]}.wav"])
        text += transcription[0]['transcription']
        probsum.append(sum(transcription[0]['probabilities']))
        problen.append(len(transcription[0]['probabilities']))
        os.remove(f"{filename[:-4]}.wav")

    probability = sum(probsum) / sum(problen)

    return text, probability



# def rmv_sw(text):
#     #funciton that removes portuguese stopwords from text

#     stop_words = stopwords.words('portuguese')
#     word_tokens = word_tokenize(text)
#     text = [w for w in word_tokens if not w in stop_words]
#     return ' '.join(text)
