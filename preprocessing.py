from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from pydub import AudioSegment

def convert_audio(audio):
    sound = AudioSegment.from_ogg(audio)
    sound = sound.set_frame_rate(16000)
    sound.export("audio.wav", format="wav")



def rmv_sw(text):
    #funciton that removes portuguese stopwords from text

    stop_words = stopwords.words('portuguese')
    word_tokens = word_tokenize(text)
    text = [w for w in word_tokens if not w in stop_words]
    return ' '.join(text)
