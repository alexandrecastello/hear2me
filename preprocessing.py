from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from pydub import AudioSegment
from urllib import request
from math import ceil

def convert_audio(audio_path,filename):
    local_file = filename
    request.urlretrieve(audio_path, local_file)
    sound = AudioSegment.from_ogg(local_file)
    sound = sound.set_frame_rate(16000)
    audiolen=30
    i=0
    subitems=ceil(len(sound)/60000*60/audiolen)

    if(subitems>0):
        for item in range(0,subitems):
            if(item+1==ceil(len(sound)/60000*60/50)):
                start=i*audiolen*1000
                end=len(sound)/60000*60*1000
                i+=1
            else:
                start=audiolen*i*1000
                end=audiolen*(i+1)*1000
                i+=1
            sound[start:end].export(f"{filename[:-4]}_{i}.wav", format="wav")
    else:
            sound[start:end].export(f"{filename[:-4]}.wav", format="wav")
    return subitems


def rmv_sw(text):
    #funciton that removes portuguese stopwords from text

    stop_words = stopwords.words('portuguese')
    word_tokens = word_tokenize(text)
    text = [w for w in word_tokens if not w in stop_words]
    return ' '.join(text)
