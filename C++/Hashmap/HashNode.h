#pragma once

#include <cstddef>

template <typename K, typename V>
class HashNode
{
public:
	HashNode(const K &key, const V &value) :
		_key(key), _value(value), _next(NULL) 
	{}

	K GetKey() const
	{
		return _key;
	}

	V GetValue() const
	{
		return _value;
	}

	void SetValue(V value)
	{
		_value = value;
	}

	HashNode *GetNext() const
	{
		return _next;
	}

	void SetNext(const HashNode *next)
	{
		_next = next;
	}
private:
	K _key;
	V _value;
	HashNode *_next;
	HashNode(const HashNode &);
	HashNode & operator=(const HashNode &);
};